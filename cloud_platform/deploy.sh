#! /bin/sh

# auto build war.
ant war

TOMCAT_DIR="/home/dawei/programs/apache-tomcat-7.0.41/"

# stop server
$TOMCAT_DIR/bin/shutdown.sh

# remove old files.
rm -rf $TOMCAT_DIR/webapps/platform
rm -rf $TOMCAT_DIR/webapps/platform.war

# copy the war to tomcat webapps.
cp platform.war $TOMCAT_DIR/webapps/

$TOMCAT_DIR/bin/startup.sh