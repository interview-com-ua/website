#!/bin/bash
# Update source code from main repository

# Pop any stashed changes
unstash() {
if [[ "$stash" =~ "No local changes to save" ]]; then
echo "* No stashed changes, not popping"
else
echo "* Popping stash..."
git stash pop
fi
}

# Pop any stashed changes and exit
rollback() {
echo
echo "Something went wrong, rolling back"
unstash
exit $1
}

# Stash any local change"
echo "* Stash any local changes"
stash=$(git stash)

remote_upstream="upstream"
remote_upstream_master_branch="master"

upstream_remote_exists=$(git branch -r --no-color | egrep "$remote_upstream/$remote_upstream_master_branch\$")

if [ -n "$upstream_remote_exists" ] && [ ! "$upstream_remote_exists" == '' ]; then
 echo "* Upstream remote branch exists ....."
else
 echo "* Adding upstream remote branch"
 git remote add upstream https://github.com/interview-com-ua/website.git
fi

echo "* Fetching from upstream "
git fetch upstream || rollback $?
echo "* Checkout local  master "
git checkout master
echo "* Merge local master and upstream/master"
git merge upstream/master || rollback $?
echo "* Push  changes into  remote  origin/master"
git push origin

unstash
read -rp "* Done. Press any key to continue..." key  
