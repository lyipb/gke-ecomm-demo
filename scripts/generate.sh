#!/bin/sh
##
# Script to deploy a Kubernetes project with a StatefulSet running a MongoDB Replica Set, to GKE.
##

# Create new GKE Kubernetes cluster (using host node VM images based on Ubuntu
# rather than ChromiumOS default & also use slightly larger VMs than default)
##gcloud container clusters create "gke-mongodb-demo-cluster" --image-type=UBUNTU --machine-type=n1-standard-2

# Configure host VM using daemonset to disable hugepages
#kubectl apply -f ../resources/hostvm-node-configurer-daemonset.yaml

# Define storage class for dynamically generated persistent volumes
# NOT USED IN THIS EXAMPLE AS EXPLICITLY CREATING DISKS FOR USE BY PERSISTENT
# VOLUMES, HENCE COMMENTED OUT BELOW
#kubectl apply -f ../resources/gce-ssd-storageclass.yaml

# Register GCE Fast SSD persistent disks and then create the persistent disks 


# echo "Creating GCE disks for pc"
for i in 0 1 2
do
    gcloud compute disks create --size 10GB --type pd-ssd pd-ssd-disk-pc-$i --zone=us-central1-c
done 
sleep 3

# echo "Creating GCE disks for pi"
for i in 0 1 2
do
    gcloud compute disks create --size 10GB --type pd-ssd pd-ssd-disk-pi-$i --zone=us-central1-c
done 
sleep 3


# Create persistent volumes using disks created above



# echo "Creating GKE Persistent Volumes for pc"
for i in 0 1 2
do
    sed -e "s/INST/${i}/g" ../resources/xfs-gce-ssd-persistentvolume-pc.yaml > /tmp/xfs-gce-ssd-persistentvolume-pc.yaml
    kubectl apply -f /tmp/xfs-gce-ssd-persistentvolume-pc.yaml
done
rm /tmp/xfs-gce-ssd-persistentvolume.yaml
sleep 3


# echo "Creating GKE Persistent Volumes for pi"
for i in 0 1 2
do
    sed -e "s/INST/${i}/g" ../resources/xfs-gce-ssd-persistentvolume-pi.yaml > /tmp/xfs-gce-ssd-persistentvolume-pi.yaml
    kubectl apply -f /tmp/xfs-gce-ssd-persistentvolume-pi.yaml
done
rm /tmp/xfs-gce-ssd-persistentvolume.yaml
sleep 3




# Create keyfile for the MongoD cluster as a Kubernetes shared secret
# TMPFILE=$(mktemp)
# /usr/bin/openssl rand -base64 741 > $TMPFILE
# kubectl create secret generic shared-bootstrap-data --from-file=internal-auth-mongodb-keyfile=$TMPFILE
# rm $TMPFILE

# Create configmap, secrets for ecommerce app
kubectl apply -f ../resources/ecomm_config_secret.yaml


# Create mongodb service with mongod stateful-set for pc
kubectl apply -f ../resources/ecomm_mongo_pc.yaml
echo

# Create mongodb service with mongod stateful-set for pi
kubectl apply -f ../resources/ecomm_mongo_pi.yaml
echo

# Create rest api for pc
kubectl apply -f ../resources/ecomm_pc.yaml
echo

# Create rest api for pi
kubectl apply -f ../resources/ecomm_pi.yaml
echo


# Wait until the final (3rd) mongod has started properly

# echo "Waiting for the 3 containers to come up (`date`)..."
# echo " (IGNORE any reported not found & connection errors)"
# sleep 30
# echo -n "  "
# until kubectl --v=0 exec mongod-2 -c mongod-container -- mongo --quiet --eval 'db.getMongo()'; do
#     sleep 5
#     echo -n "  "
# done
# echo "...mongod containers are now running (`date`)"
# echo

# # Print current deployment state
# kubectl get persistentvolumes
# echo
# kubectl get all 

