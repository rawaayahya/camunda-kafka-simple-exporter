# camunda-kafka-simple-exporter


My endeavor involves setting up Camunda on a local Kubernetes cluster while integrating a Kafka exporter. To achieve this, I referred to the Camunda documentation available at [link](https://docs.camunda.io/docs/self-managed/platform-deployment/helm-kubernetes/guides/local-kubernetes-cluster/). Following the guidelines, I made modifications to the values file `camunda-platform-core-kind-values.yaml` to incorporate an initContainer. Within this initContainer, I copied the Kafka exporter JAR into the `zeebe/lib` directory and configured all necessary environment variables required by the exporter.

For Windows users, the following requirements must be met:
- Docker Desktop
- Kind
- kubectl
- Helm

Here's a step-by-step guide to get everything up and running:

### A. Build the Exporter JAR:
- Navigate to the Maven project `zeebe-simple-monitor-main` and build the JAR file.

### B. Adding the JAR to the Zeebe Library Folder:
1. Build a Docker image where you'll place the JAR file. This image will serve as the source for copying the JAR into the Zeebe Docker image via the InitContainer configuration.
2. Use the provided Dockerfile to build the Docker image. After building, name the image "bginx-custom-22".
   
   ```bash
   docker build -t bginx-custom-22 .
   ```
   
3. Push the image to your Docker registry. First, tag the image correctly with your registry host:

   ```bash
   docker tag [OPTIONS] IMAGE[:TAG] [REGISTRYHOST/][USERNAME/]NAME[:TAG]
   ```
   
   Then push the image using the same tag:

   ```bash
   docker push NAME[:TAG]
   ```

4. Update the file `camunda-platform-core-kind-values.yaml` to reference your custom Docker image created in the previous step. Also, ensure to update the Kafka brokers with the details of your Kafka cluster.

### Kafka Cluster:
Additionally, I installed a Kafka cluster on my local machine following the instructions provided at [this URL](https://bitnami.com/stack/kafka/helm).
