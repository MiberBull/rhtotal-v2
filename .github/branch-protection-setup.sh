#!/usr/bin/env bash
# Branch protection rules for DCH Know Who Platform
# Run ONCE after creating the repo / adding collaborators.
# Requires: gh CLI authenticated with repo admin permissions.
#
# Usage: bash .github/branch-protection-setup.sh

set -euo pipefail

OWNER="MiberBull"
REPO="rhtotal-v2"

echo "==> Applying branch protection for: ${OWNER}/${REPO}"

# ── main ──────────────────────────────────────────────────────────────────────
echo "  -> main: require 1 approval, no direct push, no force push"
gh api \
  --method PUT \
  "repos/${OWNER}/${REPO}/branches/main/protection" \
  --input - <<'EOF'
{
  "required_status_checks": null,
  "enforce_admins": true,
  "required_pull_request_reviews": {
    "dismiss_stale_reviews": true,
    "require_code_owner_reviews": true,
    "required_approving_review_count": 1
  },
  "restrictions": null,
  "allow_force_pushes": false,
  "allow_deletions": false
}
EOF

# ── develop ────────────────────────────────────────────────────────────────────
echo "  -> develop: require 1 approval, no direct push"
gh api \
  --method PUT \
  "repos/${OWNER}/${REPO}/branches/develop/protection" \
  --input - <<'EOF'
{
  "required_status_checks": null,
  "enforce_admins": false,
  "required_pull_request_reviews": {
    "dismiss_stale_reviews": false,
    "require_code_owner_reviews": false,
    "required_approving_review_count": 1
  },
  "restrictions": null,
  "allow_force_pushes": false,
  "allow_deletions": false
}
EOF

echo "==> Done. Verify at: https://github.com/${OWNER}/${REPO}/settings/branches"
