mvn clean package
aws ecr get-login-password --region sa-east-1 | docker login --username AWS --password-stdin 569630210828.dkr.ecr.sa-east-1.amazonaws.com
sudo docker buildx build -t tellus-integration . --platform linux/amd64
docker tag tellus-integration:latest 569630210828.dkr.ecr.sa-east-1.amazonaws.com/tellus-integration:latest
docker push 569630210828.dkr.ecr.sa-east-1.amazonaws.com/tellus-integration:latest
