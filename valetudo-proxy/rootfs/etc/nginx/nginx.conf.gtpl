# Run nginx in foreground.
daemon off;

# This is run inside Docker.
user root;

# Pid storage location.
pid /var/run/nginx.pid;

# Set number of worker processes.
worker_processes 1;

# Enables the use of JIT for regular expressions to speed-up their processing.
pcre_jit on;

# Write error log to the add-on log.
error_log /proc/1/fd/1 error;

# Max num of simultaneous connections by a worker process.
events {
    worker_connections 512;
}

http {
    include /etc/nginx/includes/mime.types;

    access_log              off;
    client_max_body_size    4G;
    default_type            application/octet-stream;
    gzip                    on;
    keepalive_timeout       65;
    sendfile                on;
    server_tokens           off;
    tcp_nodelay             on;
    tcp_nopush              on;

    map $http_upgrade $connection_upgrade {
        default upgrade;
        ''      close;
    }

    server {
        listen 8099;
        allow 172.30.32.2;
        deny all;

        location / {
            proxy_pass http://{{ .address }};
            {{if .password}}proxy_set_header Authorization "Bearer {{b64enc nospace cat .username \":\" .password}}"{{end}}
        }
    }
}
