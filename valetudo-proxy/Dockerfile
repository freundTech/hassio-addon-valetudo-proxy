# https://developers.home-assistant.io/docs/add-ons/configuration#add-on-dockerfile
ARG BUILD_FROM
FROM $BUILD_FROM

# Execute during the build of the image
RUN apk add --no-cache nginx

# Copy root filesystem
COPY rootfs /

WORKDIR /

CMD ["/nginx-wrapper"]
