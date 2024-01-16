#!/bin/bash
# script to build a container image using buildah
#
#
# to get a container from the resulting image, mount a volume which contains config.yaml and additionally required files.
# You can use docker or podman to run the image.
#
 # example: podman run --name "artikel-test" --rm -it -p 5000:4567 -v ./config:/opt/artikel/config:z artikel:latest
#
# last update: uwe.geercken@web.de - 2023-02-18
#

# absolute path to this script
script=$(readlink -f "$0")
# folder this script is located in
script_folder=$(dirname "$script")

# base image
image_base="openjdk"
image_base_version="17"

# new image variables
artifact_id="${1}"
artifact_version="${2}"
image_author="uwe.geercken@web.de"
image_format="docker"

image_name_registry="${image_registry_docker_private}/${artifact_id}"
image_name="${artifact_id}"
image_tag="${artifact_id}:${artifact_version}"

# variables for container
working_container="artikel-working-container"
application_folder_root="/opt/artikel"
application_folder_lib="${application_folder_root}/lib"
application_folder_config="${application_folder_root}/config"
application_folder_documents="${application_folder_config}/documents"
application_folder_pdf="${application_folder_config}/pdf"
application_entrypoint="entrypoint.sh"
application_jar="artikel.jar"

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
buildah run $container mkdir "${application_folder_config}"
buildah run $container mkdir "${application_folder_documents}"
buildah run $container mkdir "${application_folder_pdf}"

# install software
export DEBIAN_FRONTEND=noninteractive
buildah run $container apt-get update
buildah run $container apt-get -yq install glabels
buildah run $container rm -rf /var/lib/apt/lists/*

# copy required files
echo "copying files to container"
buildah copy $container "${script_folder}/${application_entrypoint}" "${application_folder_root}"
buildah copy $container "${script_folder}/../${application_version}/${artifact_id}-${artifact_version}/${application_jar}" "${application_folder_root}"
#buildah copy $container "${script_folder}/config" "${application_folder_config}"
buildah copy $container "${script_folder}/../${application_version}/${artifact_id}-${artifact_version}/lib" "${application_folder_lib}"


# configuration
echo "adding container configuration"
buildah config --author "${image_author}" $container
buildah config --workingdir "${application_folder_root}" $container
buildah config --entrypoint "${application_folder_root}/${application_entrypoint}" $container

echo "committing container to image: ${image_name}"
buildah commit --format "${image_format}" $container "${image_name}"

echo "removing container: ${container}"
buildah rm $container

echo "tagging image ${image_name}: ${image_tag}"
buildah tag  "${image_name}" "${image_tag}"

echo "end buildah process"
