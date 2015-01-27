export jdbc_driver=org.postgresql.Driver
export jdbc_url="jdbc:postgresql://127.0.0.1/skill2peer"
export jdbc_usr=postgres
export jdbc_pwd=<PASSWORD>

export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_25.jdk/Contents/Home
export MVN_HOME="/Users/artem/Downloads/apache-maven-3.0.5/bin"


export PATH=$MVN_HOME:$PATH

export s2p_facebook_app_id=<FOO>
export s2p_facebook_app_secret=<BAR>

./db.command

echo "========================="
echo MVN_HOME=$MVN_HOME
echo PATH=$PATH
echo jdbc_url=$jdbc_url
echo "========================="


mvn clean spring-boot:run -P dev
