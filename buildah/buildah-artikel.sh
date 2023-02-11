#!/bin/bash
# script to build a container image using buildah and pushing
# it to the registry/artifactory.
#
# executed by maven as part of the package phase and
# using the project and dependency files.
#
# note: the 'image_registry_user' and 'image_registry_password' environment variables need to be defined.
#       the image version is triggered from the pom.xml when this script is executed.
#
# to get a container from the resulting image, mount a volume which contains the ruleengine project zip file.
# You can use docker or podman to run the image.
#
 # example: sudo podman run --name "testserver" --rm -v ./rules/:/opt/jare-server/rules:Z silent1:8082/jare-server:latest
#
# last update: uwe.geercken@web.de - 2020-04-18
#

# absolute path to this script
script=$(readlink -f "$0")
# folder this script is located in
script_folder=$(dirname "$script")

# base image
image_base="openjdk"
image_base_version="11"

# new image variables
image_name="artikel"
image_version="${1}"
image_author="uwe.geercken@web.de"
image_format="docker"
image_registry_docker_group="silent1:8082"
image_registry_docker_private="silent1:8083"

image_name_registry="${image_registry_docker_private}/${image_name}"
image_tag="${image_registry_docker_private}/${image_name}:${image_version}"

# variables for container
working_container="artikel-working-container"
application_folder_root="/opt/artikel"
application_folder_lib="${application_folder_root}/lib"
application_entrypoint="entrypoint.sh"

# make sure scripts are executable
echo "making entrypoint script executable"
chmod +x "${script_folder}/${application_entrypoint}"

# start of build
echo "start of buildah process"
echo "using working container: ${working_container}"
container=$(buildah --name "${working_container}" from ${image_base}:${image_base_version})

# create application directories
echo "creating directories in container"
buildah run $container mkdir "${application_folder_root}"

# copy required files
echo "copying files to container"
buildah copy $container "${script_folder}/${application_entrypoint}" "${application_folder_root}"
buildah copy $container "${script_folder}/lib" "${application_folder_lib}"

# configuration
echo "adding configuration to container"
buildah config --author "${image_author}" $container
buildah config --workingdir "${application_folder_root}" $container
buildah config --entrypoint "${application_folder_root}/${application_entrypoint}" $container

echo "committing container to image: ${image_name_registry}"
buildah commit --format "${image_format}" $container "${image_name_registry}"

echo "removing container: ${container}"
buildah rm $container

echo "tagging image ${image_name_registry}: ${image_tag}"
buildah tag  "${image_name_registry}" "${image_tag}"

echo "login to registry ${image_registry_docker_private}, using user: ${image_registry_user}"
buildah login -u "${image_registry_user}" -p "${image_registry_password}" "${image_registry_docker_private}"

echo "pushing image ${image_name_registry}:latest to: ${image_registry_docker_private}"
buildah push --tls-verify=false "${image_name_registry}" "docker://${image_name_registry}"
echo "pushing image ${image_tag} to: ${image_registry_docker_private}"
buildah push --tls-verify=false "${image_tag}" "docker://${image_tag}"

echo "end buildah process"
