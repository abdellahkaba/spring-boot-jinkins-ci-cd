pipeline {
	agent any

    tools{
		jdk 'jdk17'
        maven 'maven3'
    }

    stages {
		stage('Code Checkout') {
			steps {
				git branch: 'main', changelog: false, poll: false, url: 'https://github.com/abdellahkaba/spring-boot-jinkins-ci-cd'
            }
        }

        stage('OWASP Dependency Check'){
			steps{
				dependencyCheck additionalArguments: '--scan ./ --format HTML ', odcInstallation: 'db-check'
                dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
            }
        }

        stage('Sonarqube Analysis') {
			steps {
				sh ''' mvn sonar:sonar \
                    -Dsonar.host.url=http://localhost:9000/ \
                    -Dsonar.login=squ_8bfb359125e08d414acba11634beb911af69538a '''
            }
        }

        stage('Clean & Package'){
			steps{
				sh "mvn clean package -DskipTests"
            }
        }




		stage("Docker Build & Push") {
			steps {
				script {
					withDockerRegistry(credentialsId: 'DockerHub-Token', toolName: 'docker') {
						def imageName = "backend-product-api"
                		def buildTag = "${imageName}:${BUILD_NUMBER}"
                		def latestTag = "${imageName}:latest"

						sh "docker build -t ${imageName} -f Dockerfile ."
						sh "docker tag ${imageName} abdellahkaba7/${buildTag}"
						sh "docker tag ${imageName} abdellahkaba7/${latestTag}"
						sh "docker push abdellahkaba7/${buildTag}"
						sh "docker push abdellahkaba7/${latestTag}"
						env.BUILD_TAG = buildTag
            		}
        		}
   			}
		}

        stage('Vulnerability scanning'){
			steps{
				sh " trivy image abdellahkaba7/${buildTag}"
            }
        }

        stage("Staging"){
			steps{
				sh 'docker-compose up -d'
            }
        }
    }
}