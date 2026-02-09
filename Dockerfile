FROM python:3.12-slim

WORKDIR /app

COPY requirements.txt ./
RUN python -m pip install --no-cache-dir -r requirements.txt

COPY mtg_ontology/ ./mtg_ontology/

# Default CLI entrypoint; docker-compose overrides workdir + mounts the repo.
ENTRYPOINT ["python", "-m", "mtg_ontology.cli"]
