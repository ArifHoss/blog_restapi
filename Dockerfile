FROM ubuntu:latest
LABEL authors="arif"

ENTRYPOINT ["top", "-b"]