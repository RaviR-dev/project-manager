pipeline {
    agent any
    tools { 
        maven 'Maven'
		jdk 'jdk8'
    }
    triggers {
        pollSCM('H/5 * * * *')
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
    }
   
   stages
	{
	   stage('Git Checkout') {
		   steps {
				echo 'Checking out code from git'
			    checkout([$class: 'GitSCM', branches: [[name: '*/master']], 
				      doGenerateSubmoduleConfigurations: false, extensions: [], 
				      submoduleCfg: [], 
				      userRemoteConfigs: [[url: 'https://github.com/RaviR-dev/project-manager.git']]])
			    echo 'Git checkout completed'
		   }
	   }

	   stage('Build') {
		   steps {
			echo 'Starting build'
			bat "mvn clean install"
			echo 'Build completed'
		   }
	   }
	   stage('Publish Junit Report') {
		   steps {
				echo 'JUnit started'
				archiveArtifacts artifacts: 'project-manager-service/target/*.jar', fingerprint: true
				junit '**/TEST-*.xml'
			        step([$class: 'JacocoPublisher', 
				      execPattern: '**/*.exec',
				      classPattern: '**/classes',
				      sourcePattern: '**/src/main/java',
				      exclusionPattern: '**/src/test*'
				])
				echo 'JUnit completed'
		   }
		}
	}
}