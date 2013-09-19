#! /bin/sh

# run in sudo!

# auto build war.
ant war

TOMCAT_DIR="/home/chenlei/tomcat/apache-tomcat-7.0.42"

# stop server
$TOMCAT_DIR/bin/shutdown.sh

# remove old files.
rm -rf $TOMCAT_DIR/webapps/platform
rm -rf $TOMCAT_DIR/webapps/platform.war

# copy the war to tomcat webapps.
cp platform.war $TOMCAT_DIR/webapps/

$TOMCAT_DIR/bin/startup.sh

sleep 3

chmod +777 $TOMCAT_DIR/webapps/platform/WEB-INF/scripts/*
# chmod +x $TOMCAT_DIR/webapps/platform/WEB-INF/scripts/svm.sh
# chmod +x $TOMCAT_DIR/webapps/platform/WEB-INF/scripts/generate_svm_format.py
