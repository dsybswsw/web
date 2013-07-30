#!/bin/sh

# set environment variables.
export TOMCAT_HOME="/home/dawei/programs/apache-tomcat-7.0.41/"
export HADOOP_HOME="/home/dawei/programs/hadoop-1.1.2/"

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

# ORG_DATA="$ROOT/data/a2a.txt"
# MODEL="$ROOT/data/a2a.model"

# temporary directory for data storage.

# temporary directory for data storage.
TEMP_OUT=$ROOT/temp_out/
TEMP_DATA=$ROOT/train.tmp
SAMPLE_DATA=$ROOT/train.smp

# export HADOOP_HOME="/home/dawei/programs/hadoop-1.1.2/"
# export MAHOUT_HOME="/home/dawei/codes/mahout/mahout-distribution-0.7"

$HADOOP_HOME/bin/hadoop fs -rmr $TEMP_OUT

echo "remove tmp files"

$HADOOP_HOME/bin/hadoop jar $JAR_ROOT/svm.jar \
    com.datascience.svm.mapreduce.SVMMapReduceController $ORG_DATA $TEMP_OUT

echo "finish sampling the data"

$HADOOP_HOME/bin/hadoop fs -cat $TEMP_OUT/* > $TEMP_DATA

echo "output the files to temp data"

#generate to SVM training format.
$JAR_ROOT/generate_svm_format.py $TEMP_DATA $SAMPLE_DATA

echo "generate training file format"

# train a SVM model.
$HADOOP_HOME/bin/hadoop jar $JAR_ROOT/svm.jar \
    com.datascience.svm.wrapper.SVMTrainer $SAMPLE_DATA $MODEL

echo "train a svm model"

rm -f $RESULT

echo "re new the result file"

# test a model.
$HADOOP_HOME/bin/hadoop jar $JAR_ROOT/svm.jar \
    com.datascience.svm.wrapper.SVMTester $MODEL $TEST $RESULT

echo "finish test the model"