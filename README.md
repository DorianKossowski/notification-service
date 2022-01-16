# notification-service

Service responsible for sending notifications.

## Environment variables

* RabbitMQ variables
    * `RABBITMQ_HOST` - host for RabbitMQ, default `localhost`
    * `RABBITMQ_PORT` - port for RabbitMQ, default `5672`
    * `RABBITMQ_USERNAME` - username for RabbitMQ, default `guest`
    * `RABBITMQ_PASSWORD` - password for RabbitMQ, default `guest`
    * `EXCHANGE_NAME` - exchange name, default `service.exchange`
    * `QUEUE_NAME` - queue name, default `notification`
    * `QUEUE_BINDING_KEY` - binding key, default `notification.message`
* Mail variables
    * `MAIL_HOST` - smtp server host, default `localhost`
    * `MAIL_PORT` - smtp server port, default `1025`
    * `MAIL_USERNAME` - login user to smtp server, default ` `
    * `MAIL_PASSWORD` - login password to smtp server, default ` `
    * `MAIL_AUTH` - enable smtp server authentication, default `false`
    * `MAIL_TTLS` - enable a TLS-protected connection, default `true`
    * `MAIL_FROM` - from mail address, default `testing@from.com`