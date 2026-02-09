#!/usr/bin/env bash
set -euo pipefail

url="${1:-http://localhost:6333}"
timeout_s="${2:-60}"

start="$(date +%s)"
while true; do
  if curl -fsS "${url%/}/collections" >/dev/null 2>&1; then
    echo "Qdrant ready: ${url}"
    exit 0
  fi

  now="$(date +%s)"
  if (( now - start > timeout_s )); then
    echo "Timed out waiting for Qdrant at ${url} after ${timeout_s}s" >&2
    exit 1
  fi

  sleep 1
done

