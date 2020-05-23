
# Prerequisite
JCE 8

URL: https://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html


```bash
cd /tmp
wget http://download.oracle.com/otn-pub/java/jce/8/jce_policy-8.zip

unzip jce_policy-8.zip 

cd UnlimitedJCEPolicyJDK8

# backup original

sudo mv /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/lib/security/local_policy.jar /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/lib/security/local_policy.jar.original
sudo mv /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/lib/security/US_export_policy.jar /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/lib/security/US_export_policy.jar.original

# setup new one
sudo cp -rpf local_policy.jar /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/lib/security/local_policy.jar
sudo cp -rpf US_export_policy.jar /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/lib/security/US_export_policy.jar

```


