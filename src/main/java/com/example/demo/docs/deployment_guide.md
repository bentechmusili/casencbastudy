# Kubernetes Deployment Guide

## Prerequisites

* Docker installed
* Kubernetes cluster running (Minikube, Kind, AKS, EKS, GKE)
* kubectl configured
* Maven installed

## Step 1: Build the Application

```bash
mvn clean package
```

This generates the executable JAR in the target directory.

## Step 2: Build Docker Image

```bash
docker build -t bentechmusili/country-service:1.0 .
```

## Step 3: Push Image to Docker Hub

```bash
docker login
docker push bentechmusili/country-service:1.0
```

## Step 4: Deploy to Kubernetes

```bash
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
kubectl apply -f hpa.yaml
```

## Step 5: Verify Deployment

```bash
kubectl get deployments
kubectl get pods
kubectl get svc
kubectl get hpa
```

## Step 6: Access Application

```bash
kubectl get svc country-service
```

Use the EXTERNAL-IP displayed by Kubernetes.

Example:

```bash
http://<external-ip>/countries/KE
```
