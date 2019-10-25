

cd `dirname $0`
mvn -T 2C clean package
cp target/hacker-cup-jar-with-dependencies.jar hacker-cup-jar-with-dependencies.jar