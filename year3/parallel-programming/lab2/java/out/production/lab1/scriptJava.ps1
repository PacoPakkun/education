$main = "Main"
$populate = "Populate"

$NN = $args[0]
$MM = $args[1]
$n = $args[2]
$m = $args[3]
$p = $args[4]
Write-Host ""
Write-Host "--------- N=$($NN) M=$($MM) n=$($n) m=$($m) p=$($p)"
Write-Host ""
$suma = 0
$a = java $populate $NN $MM $n $m $p
for ($i = 0; $i -lt 10; $i++){
    Write-Host "Rulare" ($i+1)
    $a = java $main
    Write-Host $a
    $suma += $a
    Write-Host ""
}
$media = $suma / $i
Write-Host "Timp de executie mediu:" $media
if (!(Test-Path outJ.csv)){
    New-Item outJ.csv -ItemType File
    Set-Content outJ.csv 'Tip Matrice,Nr threads,Timp executie'
}
Add-Content outJ.csv "N=$($NN) M=$($MM) n=$($n) m=$($m),$($p),$($media)"