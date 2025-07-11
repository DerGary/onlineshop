data "external_schema" "hibernate" {
    program = [
        "./gradlew",
        "-q",
        "schema",
        "--properties", "schema-export.properties"
    ]
}
env "hibernate" {
  src = data.external_schema.hibernate.url
  dev = "docker://postgres/15/dev?search_path=public"
  migration {
    dir = "file://src/main/resources/migrations"
    format = flyway
  }
  format {
    migrate {
      diff = "{{ sql . \"  \" }}"
    }
  }
}