pipeline {
	agent any

    tools {
		maven 'Maven'   // Nom défini dans "Global Tool Configuration"
        jdk 'JDK'       // Idem
    }

    stages {
		stage('📦 Vérification des versions') {
			steps {
				echo '🔍 Affichage des versions Java et Maven'
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('📥 Récupération du code') {
			steps {
				git branch: 'main', url: 'https://github.com/abdellahkaba/spring-boot-jinkins-ci-cd.git'
            }
        }

        stage('🏗️ Compilation du projet') {
			steps {
				echo '🔨 Compilation avec Maven'
                sh 'mvn clean compile'
            }
        }

        stage('🧪 Tests unitaires') {
			steps {
				echo '✅ Exécution des tests unitaires'
                sh 'mvn test'
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
