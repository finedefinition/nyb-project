# Jib docker build
mvn compile jib:dockerBuild

# S3
aws s3 cp /home/ec2-user/app/.bin/nginx/default.conf s3://findout-content-media/nginx-default-develop-test.conf
aws s3 cp /tmp/ssr s3://findout-content-media/ssr --recursive

# Docker
docker exec -it <container_id> /bin/sh

docker cp <container_id>:<path_to_directory_inside_container> <path_on_host_machine>
docker cp <container_id>:/app/some_directory /path/on/host/some_directory

# deploy.sh
#Создайте файл со скриптом:
vi deploy.sh

#Сделайте файл исполняемым
chmod +x deploy.sh

#Запустите скрипт с выводом логов в реальном времени
sudo ./deploy.sh | tee deploy.log
sudo ./deploy.sh eu-central-1 078724128682 fdout.com test-latest | tee deploy.log

# .ssh
sudo su - ec2-user
cat ~/.ssh/authorized_keys
sudo systemctl restart sshd
sergii@MacBook-Pro-Sergii .ssh % ssh -i "~/.ssh/devops_ed" ec2-user@ec2-52-28-192-159.eu-central-1.compute.amazonaws.com
~/.ssh/devops_ed

ssh -i "fdout-devops-prod-frank.pem" ec2-user@ec2-52-59-222-79.eu-central-1.compute.amazonaws.com

# Подключение fdout-prod-db
ssh -i "fdout-devops-prod-frank.pem" -L 6100:test-lenivec-db.c7foxilwxzra.eu-central-1.rds.amazonaws.com:5432 ec2-user@ec2-3-72-64-167.eu-central-1.compute.amazonaws.com -N
# Подключение test-lenivec-db
ssh -i "fdout-devops-prod-frank.pem" -L 6543:test-lenivec-db.c7foxilwxzra.eu-central-1.rds.amazonaws.com:5432 ec2-user@ec2-3-72-64-167.eu-central-1.compute.amazonaws.com -N
# Подключение lenivec-db
ssh -i "fdout-devops-prod-frank.pem" -L 5435:lenivec-db.c7foxilwxzra.eu-central-1.rds.amazonaws.com:5432 ec2-user@ec2-18-153-155-40.eu-central-1.compute.amazonaws.com -N
# Подключение fdout-pl-prod-db
ssh -i "fdout-devops-prod-frank.pem" -L 6000:fdout-pl-prod-db.c7foxilwxzra.eu-central-1.rds.amazonaws.com:5432 ec2-user@ec2-18-153-155-40.eu-central-1.compute.amazonaws.com -N

docker cp my_container:/app ./my_code
tar -czvf app_backup.tar.gz /app
aws s3 cp /root/app_archive.tar.gz s3://findout-content-media/

# Make Dump
pg_dump -U postgres \
  -h main-db.cbwwkay8u0ua.us-east-1.rds.amazonaws.com \
  -p 5432 \
  -F c \
  -b \
  -v \
  -f fdout_pl_12_13.dump \
  fdout_pl_prod_db

# Dump restore
pg_restore -U postgres \
  -h main-db.cbwwkay8u0ua.us-east-1.rds.amazonaws.com \
  -p 5432 \
  -d ssr_db \
  -v \
  fdout_pl_12_13.dump

# Swarm
docker stack deploy -c docker-compose.yml findout


# CloudWatch logs restart
sudo vi /opt/aws/amazon-cloudwatch-agent/etc/amazon-cloudwatch-agent.json
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl -a fetch-config -m ec2 -c file:/opt/aws/amazon-cloudwatch-agent/etc/amazon-cloudwatch-agent.json -s
sudo systemctl status amazon-cloudwatch-agent

# .sh
chmod +x node_exporter_install.sh

# Prometheus:Node exporter
systemctl status node_exporter

# nginx
# Test Nginx Configuration
docker exec -it gateway nginx -t

# Check Nginx configuration inside the container
docker exec -it <nginx-container-id> cat /etc/nginx/conf.d/default.conf
sudo tail -f /var/log/nginx/access.log
sudo tail -f /var/log/nginx/error.log
sudo tail -f /var/log/nginx/access_ssr.log
sudo tail -f /var/log/nginx/error_ssr.log

# certbot
docker-compose run --rm --entrypoint "\
  certbot certonly --webroot \
  --webroot-path=/var/www/certbot \
  --email <sergiibezrukov@gmail.com> \
  --agree-tos \
  --no-eff-email \
  -d server.lenivec.pro \
  " certbot

  docker-compose run --rm certbot certonly --webroot --webroot-path=/var/www/certbot -d server.lenivec.pro --email sergiibezrukov@gmail.com --agree-tos --no-eff-email

docker service create --name certbot_temp \
  --mount type=bind,source="$(pwd)/ssl",target=/var/www/certbot \
  --network findout_default \ # Make sure it's on the same network as Nginx
  certbot/certbot \
  certonly --webroot \
  --webroot-path=/var/www/certbot \
  --email <sergiibezrukov@gmail.com> \
  --agree-tos \
  --no-eff-email \
  -d server.lenivec.pro

docker pull 078724128682.dkr.ecr.eu-central-1.amazonaws.com/fdout.com:develop-latest
docker pull 078724128682.dkr.ecr.eu-central-1.amazonaws.com/ssr.fdout.com:develop_ssr-latest



