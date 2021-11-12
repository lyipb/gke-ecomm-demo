#!/bin/sh
##
# Script to remove/undepoy all project resources from GKE & GCE.
##

# Delete mongod stateful set + mongodb service + secrets + host vm configuer daemonset
# kubectl delete statefulsets mongod
# kubectl delete services mongodb-service
# kubectl delete secret shared-bootstrap-data
# kubectl delete daemonset hostvm-configurer
# sleep 3

# Delete persistent volume claims for pc
for i in  0 1 2 
do
    kubectl delete pvc mongodb-persistent-storage-claim-pc-mongo-$i
done
sleep 20

# Delete persistent volume claims for pi
for i in  0 1 2 
do
    kubectl delete pvc mongodb-persistent-storage-claim-pi-mongo-$i
done
sleep 20

# Delete persistent volumes for pc
for i in 0 1 2 
do
    kubectl delete persistentvolumes data-volume-pc-$i
done
sleep 20

# Delete persistent volumes for pi
for i in 0 1 2 
do
    kubectl delete persistentvolumes data-volume-pi-$i
done
sleep 20

# Delete GCE disks for pc
for i in 0 1 2 
do
    gcloud -q compute disks delete pd-ssd-disk-pc-$i --zone=us-central1-c
done

# Delete GCE disks for pi
for i in 0 1 2 
do
    gcloud -q compute disks delete pd-ssd-disk-pi-$i --zone=us-central1-c
done

# Delete whole Kubernetes cluster (including its VM instances)
gcloud -q container clusters delete "gke-ecomm-demo-cluster"

gcloud container images delete "gcr.io/curious-ellipse-318103/ecommerce-product-catalog" --quiet

gcloud container images delete "gcr.io/curious-ellipse-318103/ecommerce-product-inventory" --quiet

