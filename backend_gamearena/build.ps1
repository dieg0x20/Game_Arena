# Navega até o diretório do script
Set-Location -Path (Split-Path -Parent $MyInvocation.MyCommand.Definition)

# Construir a imagem Docker com o nome "gamearena"
docker build -t gamearena .

# Verifica se a construção foi bem-sucedida
if ($?) {
    Write-Host "Imagem Docker 'gamearena' construída com sucesso!"
} else {
    Write-Host "Erro ao construir a imagem Docker."
}
