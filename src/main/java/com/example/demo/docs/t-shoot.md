# Kubernetes Troubleshooting Guide

## Check Pod Status

```bash
kubectl get pods
```

Possible states:

* Running
* Pending
* CrashLoopBackOff
* ImagePullBackOff

## View Pod Logs

```bash
kubectl logs <pod-name>
```

Example:

```bash
kubectl logs country-service-7f8bcd7c5f-xk8j9
```

## Describe Pod

```bash
kubectl describe pod <pod-name>
```

Useful for:

* Failed scheduling
* Probe failures
* Image pull errors

## Verify Deployment

```bash
kubectl get deployment
kubectl describe deployment country-service
```

## Verify Service

```bash
kubectl get svc
kubectl describe svc country-service
```

## Test Application Internally

```bash
kubectl exec -it <pod-name> -- curl localhost:80/actuator/health
```

Expected:

```json
{
  "status":"UP"
}
```

## Check HPA

```bash
kubectl get hpa
```

If metrics are unavailable:

```bash
kubectl get apiservices
kubectl get deployment metrics-server -n kube-system
```

## Common Errors

### CrashLoopBackOff

Cause:

* Application startup failure
* Incorrect configuration

Resolution:

```bash
kubectl logs <pod-name>
```

### ImagePullBackOff

Cause:

* Incorrect image name
* Private registry access issue

Resolution:

Verify image exists and credentials are configured.

### Readiness Probe Failed

Cause:

* Incorrect endpoint
* Application not ready

Resolution:

Verify:

```bash
/actuator/health
```

returns HTTP 200.

### Service Unreachable

Cause:

* Incorrect targetPort
* Service selector mismatch

Resolution:

Verify labels:

```bash
kubectl get pods --show-labels
kubectl describe svc country-service
```
