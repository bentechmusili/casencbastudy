1. Build application
   mvn clean package

2. Build Docker image
   docker build -t country-service:1.0 .

3. Push image
   docker push your-repo/country-service:1.0

4. Deploy to Kubernetes
   kubectl apply -f deployment.yaml
   kubectl apply -f service.yaml
   kubectl apply -f hpa.yaml

5. Check pods
   kubectl get pods

6. Check logs
   kubectl logs -f <pod-name>
