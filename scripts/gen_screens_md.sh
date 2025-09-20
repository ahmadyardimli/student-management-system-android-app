#!/usr/bin/env bash
set -euo pipefail

mkdir -p docs
out="docs/SCREENSHOTS.md"

{
  echo "# Screenshots"
  echo
  echo "_Auto-generated from files in \`docs/screenshots/\`._"
  echo
  echo '<p align="center">'
  find docs/screenshots -maxdepth 1 -type f \( -iname '*.png' -o -iname '*.jpg' -o -iname '*.jpeg' -o -iname '*.gif' \) \
    | sort | while read -r f; do
      bn="$(basename "$f")"
      alt="${bn%.*}"
      printf '<img src="screenshots/%s" alt="%s" width="260" style="margin:6px;" />\n' "$bn" "$alt"
    done
  echo '</p>'
} > "$out"

echo "Wrote $out  ($(wc -l < "$out") lines)"
