$main = "lab1.exe"
$populate = "populate.exe"

$NN = $args[0]
$MM = $args[1]
$n = $args[2]
$m = $args[3]
$p = $args[4]
Write-Host ""
Write-Host "--------- N=$($NN) M=$($MM) n=$($n) m=$($m) p=$($p)"
Write-Host ""
$suma = 0
$a = (cmd /c .\$populate $NN $MM $n $m $p 2`>`&1)
for ($i = 0; $i -lt 10; $i++){
    Write-Host "Rulare" ($i+1)
    $a = (cmd /c .\$main 2`>`&1)
    Write-Host $a
    $suma += $a
    Write-Host ""
}
$media = $suma / $i
Write-Host "Timp de executie mediu:" $media
if (!(Test-Path outC.csv)){
    New-Item outC.csv -ItemType File
    Set-Content outC.csv 'Tip Matrice,Tip alocare,Nr threads,Timp executie'
}
Add-Content outC.csv "N=$($NN) M=$($MM) n=$($n) m=$($m),dinamic,$($p),$($media)"