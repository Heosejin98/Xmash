.PHONY: up
up:
	docker-compose -f ./docker-compose.yml up

.PHONY: down
down:
	docker-compose -f ./docker-compose.yml down

.PHONY: fclean
fclean:
	docker-compose -f ./docker-compose.yml down -v
	docker rmi $(docker images -q)

.PHONY: re
re: fclean up
