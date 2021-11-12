#!/bin/sh
##
# Script to just undeploy the MongoDB Service & StatefulSet but nothing else.
##

# Just delete mongod stateful set + mongodb service onlys (keep rest of k8s environment in place)
kubectl delete -f ../resources/ecomm_config_secret.yaml

kubectl delete -f ../resources/ecomm_pc.yaml
kubectl delete -f ../resources/ecomm_pi.yaml


kubectl delete -f ../resources/ecomm_mongo_pc.yaml
kubectl delete -f ../resources/ecomm_mongo_pi.yaml

