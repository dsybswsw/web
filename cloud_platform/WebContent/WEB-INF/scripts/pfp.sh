#!/bin/sh

# set environment variables.
export TOMCAT_HOME="/home/dawei/programs/apache-tomcat-7.0.41/"
export HADOOP_HOME="/home/dawei/programs/hadoop-1.-0.4/"
# ROOT="$TOMCAT_HOME/webapps/platform/WEB-INF/"
ROOT="${TOMCAT_HOME}/webapps/models/"
JAR_ROOT="${TOMCAT_HOME}/webapps/platform/WEB-INF/scripts/"
# input parameters for training data and model.
MINSUPPORT=$1
MAXHEAPSIZE=$2
INPUT=$3
RESULT=$4

echo "MINSUPPORT=$MINSUPPORT"
echo "MAXHEAPSIZE=$MAXHEAPSIZE"
echo "INPUT_DIR=$INPUT"
echo "OUTPUT_DIR=$OUTPUT"

# ORG_DATA="$ROOT/data/a2a.txt"
# MODEL="$ROOT/data/a2a.model"

# temporary directory for data storage.

# temporary directory for data storage.
#TEMP_OUT=$ROOT/temp_out/
#EMP_DATA=$ROOT/train.tmp
#SAMPLE_DATA=$ROOT/train.smp
LOCAL_DIR=$ROOT/pfp/
OUTPUT=$ROOT/pfp_out
# export HADOOP_HOME="/home/dawei/programs/hadoop-1.1.2/"
# export MAHOUT_HOME="/home/dawei/codes/mahout/mahout-distribution-0.7"


#$HADOOP_HOME/bin/hadoop fs -rmr $TEMP_OUT
rm -rf $OUTPUT
rm -rf $LOCAL_DIR && mkdir -p $LOCAL_DIR
echo "make result document"

cp $JAR_ROOT/data3 $LOCAL_DIR/

java -cp $JAR_ROOT/mahout_pfp.jar \
   com.csy.pfp.TestPFP $MINSUPPORT $MAXHEAPSIZE $INPUT $OUTPUT

echo "finish Parallel FPGrowth"
#$HADOOP_HOME/bin/hadoop fs -get $OUTPUTpart-r-00000 $LOCAL_DIR
cp $OUTPUT $LOCAL_DIR/
java -cp $JAR_ROOT/mahout_pfp.jar \
    com.csy.dealData.transResult $OUTPUT

mv $ROOT/fresult.txt $RESULT
