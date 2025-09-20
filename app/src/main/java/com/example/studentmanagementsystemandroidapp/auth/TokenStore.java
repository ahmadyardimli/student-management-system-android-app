package com.example.studentmanagementsystemandroidapp.auth;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
public class TokenStore {
    private static final String PREF_SECURE = "AuthPrefs.secure";
    // old file name I previously used
    private static final String PREF_LEGACY = "AuthPrefs";
    private static final String KEY_AT = "token_key";
    private static final String KEY_RT = "refresh_token_key";
    private static final String KEY_UID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROLE = "role";
    private static final String KEY_USER_TYPE = "user_type";

    private final Context appContext;
    private final SharedPreferences secureSp; // Encrypted (or fallback)
    private final SharedPreferences legacySp; // old

    public TokenStore(Context context) {
        this.appContext = context.getApplicationContext();

        SharedPreferences encryptedPreferences;
        try {
            MasterKey masterKey = new MasterKey.Builder(appContext)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            encryptedPreferences = EncryptedSharedPreferences.create(
                    appContext,
                    PREF_SECURE, // different name from legacy
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            // Last-resort fallback (it is not ideal, but prevents crashes if Keystore unavailable)
            encryptedPreferences = appContext.getSharedPreferences(PREF_SECURE, Context.MODE_PRIVATE);
        }

        this.secureSp = encryptedPreferences;
        this.legacySp = appContext.getSharedPreferences(PREF_LEGACY, Context.MODE_PRIVATE);

        migrateLegacyIfAny();
    }

    // Move any old plaintext values into the secure store, then delete legacy file.
    private void migrateLegacyIfAny() {
        try {
            boolean hasLegacy =
                    legacySp.contains(KEY_AT) ||
                            legacySp.contains(KEY_RT) ||
                            legacySp.contains(KEY_UID) ||
                            legacySp.contains(KEY_USERNAME) ||
                            legacySp.contains(KEY_ROLE) ||
                            legacySp.contains(KEY_USER_TYPE);

            if (!hasLegacy) return;

            SharedPreferences.Editor ed = secureSp.edit();
            if (legacySp.contains(KEY_AT))        ed.putString(KEY_AT, legacySp.getString(KEY_AT, null));
            if (legacySp.contains(KEY_RT))        ed.putString(KEY_RT, legacySp.getString(KEY_RT, null));
            if (legacySp.contains(KEY_UID))       ed.putInt(KEY_UID, legacySp.getInt(KEY_UID, -1));
            if (legacySp.contains(KEY_USERNAME))  ed.putString(KEY_USERNAME, legacySp.getString(KEY_USERNAME, null));
            if (legacySp.contains(KEY_ROLE))      ed.putString(KEY_ROLE, legacySp.getString(KEY_ROLE, null));
            if (legacySp.contains(KEY_USER_TYPE)) ed.putString(KEY_USER_TYPE, legacySp.getString(KEY_USER_TYPE, null)); // ← add this
            ed.apply();

            // Remove legacy file so EncryptedSharedPreferences never touches plaintext data
            appContext.deleteSharedPreferences(PREF_LEGACY);
        } catch (Throwable ignored) {
            // If anything fails, just delete legacy to avoid future decrypt attempts
            appContext.deleteSharedPreferences(PREF_LEGACY);
        }
    }

    public void saveOnLogin(String accessToken, String refreshToken, int userId, String username, String role) {
        secureSp.edit()
                .putString(KEY_AT, accessToken)
                .putString(KEY_RT, refreshToken)
                .putInt(KEY_UID, userId)
                .putString(KEY_USERNAME, username)
                .putString(KEY_ROLE, role)
                .apply();
    }

    public void saveTokens(String accessToken, String refreshToken) {
        SharedPreferences.Editor ed = secureSp.edit();
        if (accessToken != null) ed.putString(KEY_AT, accessToken);
        if (refreshToken != null) ed.putString(KEY_RT, refreshToken);
        ed.apply();
    }

    public void saveOnLogin(String accessToken, String refreshToken, int userId,
                            String username, String role, String userType) {
        secureSp.edit()
                .putString(KEY_AT, accessToken)
                .putString(KEY_RT, refreshToken)
                .putInt(KEY_UID, userId)
                .putString(KEY_USERNAME, username)
                .putString(KEY_ROLE, role)
                .putString(KEY_USER_TYPE, userType)
                .apply();
    }

    public String getUserType() { return secureSp.getString(KEY_USER_TYPE, null); }


    public String getAccessToken()  { return secureSp.getString(KEY_AT, null); }
    public String getRefreshToken() { return secureSp.getString(KEY_RT, null); }
    public int getUserId()          { return secureSp.getInt(KEY_UID, -1); }
    public String getUsername()     { return secureSp.getString(KEY_USERNAME, null); }
    public String getRole()         { return secureSp.getString(KEY_ROLE, null); }

    // Clear tokens safely. So never crash the app.
    public void clear() {
        try {
            secureSp.edit().clear().apply();
        } catch (Throwable ignored) {
            // If EncryptedSharedPreferences misbehaves, nuke the file
            try { appContext.deleteSharedPreferences(PREF_SECURE); } catch (Throwable ignored2) {}
        }
        // Also remove any old plaintext file just in case
        try { appContext.deleteSharedPreferences(PREF_LEGACY); } catch (Throwable ignored3) {}
    }
}