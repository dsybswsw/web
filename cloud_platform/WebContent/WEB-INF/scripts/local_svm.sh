#!/bin/sh

# author : Shiwei Wu

# set environment variables.
echo "set running environment"
export TOMCAT_HOME="/home/dawei/programs/apache-tomcat-7.0.41/"
# export HADOOP_HOME="/home/dawei/programs/hadoop-1.1.2/"

# root workspace directory.
# ROOT="$TOMCAT_HOME/webapps/platform/WEB-INF/"
ROOT="${TOMCAT_HOME}/webapps/models/"
JAR_ROOT="${TOMCAT_HOME}/webapps/platform/WEB-INF/scripts/"

# input parameters for training data and model.
ORG_DATA=$1
MODEL=$2
TEST=$3
RESULT=$4

echo $ORG_DATA
echo $MODEL
echo $TEST
echo $RESULT

echo "start training a model"
java -jar $JAR_ROOT/svm_train.jar $ORG_DATA $MODEL
echo "train a svm model"

java -jar $JAR_ROOT/svm_tester.jar $MODEL $TEST $RESULT

echo "finish test the model"