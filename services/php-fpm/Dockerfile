FROM php:7.1-fpm-alpine

#RUN pecl install redis-4.0.1 \
#    && docker-php-ext-enable redis

RUN echo http://dl.alpinelinux.org/alpine/latest-stable/community/ >> /etc/apk/repositories \
    && apk add --no-cache autoconf g++ make \
    && pecl install -o -f redis \
    && docker-php-ext-enable redis.so \
    && pecl clear-cache
