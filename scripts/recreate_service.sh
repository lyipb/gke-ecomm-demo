#!/bin/sh


kubectl apply -f ../resources/ecomm_config_secret.yaml
echo

kubectl apply -f ../resources/ecomm_mongo_pc.yaml
echo

# Create mongodb service with mongod stateful-set for pi
kubectl apply -f ../resources/ecomm_mongo_pi.yaml
echo

kubectl apply -f ../resources/ecomm_pc.yaml
echo

# Create mongodb service with mongod stateful-set for pi
kubectl apply -f ../resources/ecomm_pi.yaml
echo
