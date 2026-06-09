
set -e


JAR_PATH=$(ls lib/postgresql*.jar 2>/dev/null | head -n1 || true)

if [ -z "$JAR_PATH" ]; then
  echo "Erro: driver JDBC do PostgreSQL não encontrado em lib/. Coloque o JAR (ex: postgresql-<versao>.jar) em lib/"
  exit 1
fi

echo "Usando driver: $JAR_PATH"

mkdir -p out
javac -d out -cp "$JAR_PATH" $(find src -name "*.java")
java -cp out:"$JAR_PATH" server.Main
