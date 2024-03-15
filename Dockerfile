FROM nginx


LABEL description="CBO MFE Shell"

LABEL maintainer="dl.global.ifs.trs.it.cfm@rs2i.com"



ARG APP_NAME="cbo-mfe-shell"

COPY --chown=101:101 index.html /usr/share/nginx/html
COPY --chown=101:101 zeebe-exporter-2.7.3-SNAPSHOT.jar /etc/nginx/zeebe-exporter-2.7.3-SNAPSHOT.jar




RUN rm -rf /usr/share/nginx/html/*



RUN chown -R 101:101 /var/cache/nginx && \

    chmod -R 755 /usr/share/nginx/html && \

        chmod -R 777 /etc/nginx && \

    chmod -R 755 /var/cache/nginx



USER nginx



EXPOSE 8080



ENTRYPOINT ["nginx", "-g", "daemon off;"]