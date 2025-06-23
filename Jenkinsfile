pipeline {
	agent any

    tools {
		maven 'Maven'              // Nom défini dans "Global Tool Configuration"
        jdk 'JDK'                  // Nom défini aussi dans "Global Tool Configuration"
    }

    stages {

		stage('📥 Récupération du code') {
			steps {
				git branch: 'main', url: 'https://github.com/abdellahkaba/spring-boot-jinkins-ci-cd.git'
            }
        }

        stage('OWASP Dependency Check') {
			steps {
				catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
					dependencyCheck additionalArguments: '--scan ./ --format HTML --format XML --disableHostedSuppressions',
                             odcInstallation: 'db-check'
            		dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
        		}
   			 }
		}


        stage('📦 Vérification des versions') {
			steps {
				script {
					if (isUnix()) {
						sh 'mvn --version'
                        sh 'java --version'
                    } else {
						bat 'mvn --version'
                        bat 'java --version'
                    }
                }
            }
        }

        stage('🏗️ Compilation du projet') {
			steps {
				script {
					if (isUnix()) {
						sh 'mvn clean package'
                    } else {
						bat 'mvn clean package'
                    }
                }
            }
        }

        stage('🧪 Tests unitaires') {
			steps {
				script {
					echo '✅ Exécution des tests unitaires'
                    if (isUnix()) {
						sh 'mvn test -Dspring-boot.run.profiles=test'
                    } else {
						bat 'mvn test -Dspring-boot.run.profiles=test'
                    }
                }
            }
        }
    }

    post {
		success {
			echo '🎉 Build et tests réussis !'
        }
        failure {
			echo '❌ Échec du pipeline.'
        }
    }
}
