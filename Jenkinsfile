pipeline {
	agent any

    tools {
		maven 'Maven'   // Nom défini dans "Global Tool Configuration"
        jdk 'JDK'       // Idem
    }




    stages {

		stage('📥 Récupération du code') {
			steps {
				git branch: 'main', url: 'https://github.com/abdellahkaba/spring-boot-jinkins-ci-cd.git'
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
					if(isUnix()) {
						echo '✅ Exécution des tests unitaires'
                		sh 'mvn test "-Dspring-boot.run.profiles=test"'
					}else {
						echo '✅ Exécution des tests unitaires'
                		sh 'mvn test "-Dspring-boot.run.profiles=test"'
					}
				}

            }
        }

        //stage("Staging"){
		//	steps{
		//		sh 'docker-compose up -d'
        //    }
        //}
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
