pipeline {
	agent any
	    // ✨ AJOUT : Déclencheurs automatiques
    triggers {
		// Avec ngrok : déclenchement instantané via webhook
        githubPush()
        // Backup : polling SCM au cas où
        pollSCM('H/5 * * * *')
    }

    tools {
		maven 'Maven'              // Nom défini dans "Global Tool Configuration"
        jdk 'JDK'                  // Nom défini aussi dans "Global Tool Configuration"
    }

    environment {
		SONAR_SCANNER_HOME = tool 'SonarQubeScanner'
        SONAR_TOKEN = credentials('sonar-token')
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

        stage('SonarQube Analysis') {
			steps {
				withSonarQubeEnv('SonarQube') {
					script {
						def scannerHome = tool 'SonarQubeScanner'
                        withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN_SECURE')]) {
							if (isUnix()) {
								sh """
                                    ${scannerHome}/bin/sonar-scanner \
                                    -Dsonar.projectKey=gestion-produits \
                                    -Dsonar.projectName='gestion-produits' \
                                    -Dsonar.java.binaries=target/classes \
                                    -Dsonar.sources=src/main/java \
                                    -Dsonar.tests=src/test/java \
                                    -Dsonar.junit.reportPaths=target/surefire-reports \
                                    -Dsonar.jacoco.reportPaths=target/jacoco.exec \
                                    -Dsonar.token=$SONAR_TOKEN_SECURE \
                                    -Dsonar.host.url=http://host.docker.internal:9000
                                """
                            } else {
								bat """
                                    ${scannerHome}/bin/sonar-scanner \
                                    -Dsonar.projectKey=gestion-produits \
                                    -Dsonar.projectName='gestion-produits' \
                                    -Dsonar.java.binaries=target/classes \
                                    -Dsonar.sources=src/main/java \
                                    -Dsonar.tests=src/test/java \
                                    -Dsonar.junit.reportPaths=target/surefire-reports \
                                    -Dsonar.jacoco.reportPaths=target/jacoco.exec \
                                    -Dsonar.token=$SONAR_TOKEN_SECURE \
                                    -Dsonar.host.url=http://host.docker.internal:9000
                                """
                            }
                        }
                    }
                }
            }
        }

		stage('Vérif Docker') {
			steps {
				sh 'docker --version'
        		sh 'docker ps'
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
