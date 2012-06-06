#!/bin/sh

LOCAL_MVN_REPO="../temula_mvn_repo/"
COMMAND=" mvn -DaltDeploymentRepository=snapshot-repo::default::file:$LOCAL_MVN_REPO/snapshots  clean deploy"
echo $COMMAND
$COMMAND

COMMAND="cd $LOCAL_MVN_REPO"
echo $LOCAL_MVN_REPO
$COMMAND

COMMAND="git add *"
echo $COMMAND
$COMMAND

COMMAND="git commit -am \"snapshot\""
echo $COMMAND
$COMMAND


COMMAND="git push origin master"
echo $COMMAND
$COMMAND

echo "make sure to update pom.xml with updated revision number "
