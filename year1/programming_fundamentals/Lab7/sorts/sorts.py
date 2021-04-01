def selectionSort(l, *, key = lambda x: x, reversed = False, cmp = None):
    """
    sort the element of the list
    l - list of element
    return the ordered list (l[0]<l[1]<...)
    """
    for i in range(0,len(l)-1):
        for j in range(i+1,len(l)):
            if reversed:
                if cmp:
                    greaterThan = cmp(l[j], l[i])
                else:
                    greaterThan = key(l[j]) > key(l[i])
                if greaterThan:
                    aux = l[i]
                    l[i] = l[j]
                    l[j] = aux
            else: 
                if cmp:
                    lowerThan = cmp(l[j], l[i])
                else:
                    lowerThan = key(l[j]) < key(l[i])
                if lowerThan:
                    aux = l[i]
                    l[i] = l[j]
                    l[j] = aux
    return l
                
def shakeSort(a, *, key = lambda x: x, reversed = False):
    n = len(a) 
    swapped = True
    start = 0
    end = n-1
    while (swapped == True): 
        swapped = False
        for i in range (start, end):
            if reversed:
                if (key(a[i]) < key(a[i + 1])) : 
                    a[i], a[i + 1]= a[i + 1], a[i] 
                    swapped = True
            elif (key(a[i]) > key(a[i + 1])) : 
                    a[i], a[i + 1]= a[i + 1], a[i] 
                    swapped = True
        if (swapped == False): 
            break
        swapped = False
        end = end-1
        for i in range(end-1, start-1, -1): 
            if reversed:
                if (key(a[i]) < key(a[i + 1])): 
                    a[i], a[i + 1] = a[i + 1], a[i] 
                    swapped = True
            elif (key(a[i]) > key(a[i + 1])): 
                a[i], a[i + 1] = a[i + 1], a[i] 
                swapped = True
        start = start + 1
    return a