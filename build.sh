

cd `dirname $0`
mvn -T 2C clean package

rm -rf hacker-cup-jar-with-dependencies.jar
cp target/hacker-cup-jar-with-dependencies.jar hacker-cup-jar-with-dependencies.jar