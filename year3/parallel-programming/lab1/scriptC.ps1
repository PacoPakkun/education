$param1 = $args[0] # Nume fisier java
$N=10000
$M=10
$param2 = $args[1] # n
$param3 = $args[2] # m
$param4 = $args[3] # No of threads
$param5 = $args[4] # No of runs


# Executare exe in cmd mode

$suma = 0

for ($i = 0; $i -lt $param5; $i++){
    Write-Host "Rulare" ($i+1)
    $a = (cmd /c .\$param1 $param2 $param3 $param4 2`>`&1)
    Write-Host $a
    $suma += $a
    Write-Host ""
}
$media = $suma / $i
Write-Host "Timp de executie mediu:" $media

# Creare fisier .csv
if (!(Test-Path outC.csv)){
    New-Item outC.csv -ItemType File
    #Scrie date in csv
    Set-Content outC.csv 'Tip Matrice,Tip alocare,Nr threads,Timp executie'
}

# Append
Add-Content outC.csv "N=$($N) M=$($M) n=$($args[1]) m=$($args[2]),dinamic,$($args[3]),$($media)"