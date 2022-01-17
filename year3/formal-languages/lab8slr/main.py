#	SLR Parser
#	Simple LR parser implementation

# no imports ;)


"""
Constants

"""

DOT = '.'
EPSILON = ''
DOLLAR = '$'
GOTO = "goto"
SHIFT = "shift"
REDUCE = "reduce"
ACCEPT = "accept"

"""
Utility functions

"""


def attach_dot(production):
    (lhsp, rhsp) = production
    return [lhsp, DOT + rhsp]


def symbol_after_dot(item):
    (lhsi, rhsi) = item
    if rhsi.endswith(DOT):
        return EPSILON
    return rhsi.split(DOT)[1][0]


def progress_dot(item):
    (lhsi, rhsi) = item
    ls = list(rhsi)
    i = ls.index(DOT)
    ls[i], ls[i + 1] = ls[i + 1], ls[i]
    return [lhsi, EPSILON.join(ls)]


def remove_dot(item):
    (lhsi, rhsi) = item
    return [lhsi, rhsi.replace(DOT, EPSILON)]


"""
Construct LR(0) items

"""


def construct_items(G):
    nonterminals = set([lhs for (lhs, rhs) in G])
    terminals = set([t for (lhs, rhs) in G for t in rhs if t not in nonterminals])

    """
    closure of an item :
    if symbol after dot is non terminal
        add its productions
    repeat until no new item is added

    """

    def closure(I):
        J = list(I)
        done = False
        while not done:
            done = True
            for item in J:
                B = symbol_after_dot(item)
                if B not in nonterminals:
                    continue
                for production in G:
                    (lhsp, rhsp) = production
                    if lhsp != B:
                        continue
                    new_item = attach_dot(production)
                    if new_item in J:
                        continue
                    J.append(list(new_item))
                    done = False
        return J

    """
    goto(I,X)	:
    Transition if X occurs at state I
    creates new lr(0) item

    """

    def goto(I, X):
        J = []
        for item in I:
            B = symbol_after_dot(item)
            if B == X:
                new_item = progress_dot(item)
                J.append(list(new_item))
        return J

    """
    items()	:
    Get LR(0) items
    Starts from first production
    make possible gotos
    until no new item is added

    """

    def items():
        C = [closure([attach_dot(G[0])])]
        S = list(nonterminals.union(terminals))
        goto_moves = {}
        done = False
        while not done:
            done = True
            for index, I in enumerate(C):
                goto_moves[index] = [None] * len(S)
                for symbol in S:
                    J = closure(goto(I, symbol))
                    if len(J) == 0:
                        continue
                    if J not in C:
                        C.append(J)
                        done = False
                    goto_moves[index][S.index(symbol)] = C.index(J)

        return C, S, goto_moves

    return items


"""
get first and follow for non-terminals (cached)


"""


def get_first_and_follow(G):
    nonterminals = set([lhs for (lhs, rhs) in G])
    terminals = set([t for (lhs, rhs) in G for t in rhs if t not in nonterminals])
    first_cache = {}
    follow_cache = {}
    FIRST_SYMBOL = G[0][0]

    """
    first(X) :
    if first symbol in rhs 
        is terminal or epsilon
            add to result
        is non terminal other than x
            add its first to result
            see next if its first contains epsilon

    """

    def first(X):
        if X in first_cache:
            return first_cache[X]
        result = set()
        X_prod = [P for P in G if P[0] == X]
        for production in X_prod:
            (lhsp, rhsp) = production
            for index, symbol in enumerate(rhsp):
                if symbol == X:
                    break
                if symbol in terminals.union(EPSILON):
                    result = result.union(symbol)
                    break
                first_y = first(symbol)
                result = result.union(first_y - {EPSILON})
                if EPSILON not in first_y:
                    break
                if index == len(rhsp) - 1:
                    result = result.union({EPSILON})
        first_cache[X] = result
        return first_cache[X]

    """
    first_of(beta) :
    same as first except beta may contain any number of symbols

    """

    def first_of(beta):
        result = set()
        for index, symbol in enumerate(beta):
            if symbol in terminals.union({EPSILON}):
                result = result.union({symbol})
                break
            first_y = first(symbol)
            result = result.union(first_y - {EPSILON})
            if EPSILON not in first_y:
                break
            if index == len(beta) - 1:
                result = result.union({EPSILON})
        return result

    """
    follow(X)	:
        follow of first symbol is dollar
        for every production containing X in rhs
        find first of portion right to X
        if it is epsilon or contains epsilon
            add follow of lhs to result

    """

    def follow(X):
        if X in follow_cache:
            return follow_cache[X]
        result = set()
        if X == FIRST_SYMBOL:
            result = result.union({DOLLAR})
        X_prod = [P for P in G if X in P[1]]
        for production in X_prod:
            (lhsp, rhsp) = production
            for index, symbol in enumerate(rhsp):
                if symbol != X:
                    continue
                if (index == len(rhsp) - 1) and (lhsp != symbol):
                    result = result.union(follow(lhsp))
                    continue
                first_beta = first_of(rhsp[index + 1:])
                result = result.union(first_beta - {EPSILON})
                if (EPSILON in first_beta) and (lhsp != symbol):
                    result = result.union(follow(lhsp))

        follow_cache[X] = result
        return follow_cache[X]

    return first, follow


"""
Construct Parsing Table for G
	for transitions of FSM
		if transition symbol is terminal add shift move
		else add goto move
	for all items containing productions of type [A->alpha.]
		add reduce move to follow of lhs of given production index
		add accept move if first prouction

"""


def construct_parsing_table(G):
    items = construct_items(G)
    states, symbols, transitions = items()
    first, follow = get_first_and_follow(G)
    symbols.append(DOLLAR)
    table = [dict() for i in states]
    nonterminals = set([s for s in symbols if s.isupper()])
    terminals = set([s for s in symbols if s not in nonterminals])

    for i in range(len(states)):
        for j in range(len(symbols) - 1):
            symbol = symbols[j]
            if transitions[i][j] == None:
                continue
            if symbol in nonterminals:
                table[i][symbol] = [GOTO, transitions[i][j]]
            else:
                table[i][symbol] = [SHIFT, transitions[i][j]]

    for index, I in enumerate(states):
        for item in I:
            B = symbol_after_dot(item)
            if B != EPSILON:
                continue
            (lhsi, rhsi) = item
            prod_index = G.index(remove_dot(item))
            if prod_index == 0:
                table[index][DOLLAR] = [ACCEPT, index]
                break
            follow_left = follow(lhsi)
            for symbol in follow_left:
                sym_index = symbols.index(symbol)
                if symbol in table[index]:
                    print("Conflict in state-symbol: ", index, symbol)
                    break
                table[index][symbol] = [REDUCE, prod_index]

    return table


"""
LR Parsing Algorithm	:
	buffer contains input string with dollar at end
	state stack with top represents current state [initial = 0]
	symbol stack is shows derivation state
	find action for current [state,symbol]
	if shift then push new state and get next symbol
	if reduce 
		pop given production [A->alpha]
		pop states upto length of alpha
		push goto move of new state on symbol A
	if accept
		Done :)


"""


def get_lr_parser(G):
    table = construct_parsing_table(G)
    print('a')

    def parser(input_string):
        buf = list(input_string)
        buf.append(DOLLAR)
        state_stack = [0]
        symbol_stack = []
        action_string = ""
        index = 0

        while True:
            symbol = buf[index]
            s = state_stack[-1]
            if symbol not in table[s]:
                action_string = "Parsing error at " + str(index) + " symbol " + symbol
                print(action_string)
                break
            (action, value) = table[s][symbol]

            if action == SHIFT:
                action_string = "Shift " + symbol
                symbol_stack.append(symbol)
                state_stack.append(value)
                index += 1

            if action == REDUCE:
                (lhsp, rhsp) = G[value]
                for i in range(len(rhsp)):
                    state_stack.pop()
                    symbol_stack.pop()
                s = state_stack[-1]
                state_stack.append(table[s][lhsp][1])
                symbol_stack.append(lhsp)
                action_string = "Reduce " + lhsp + " -> " + rhsp

            if action == ACCEPT:
                action_string = "Accepted :)"
                print(action_string)
                break

            print(''.join(symbol_stack), '\t', action_string)

    return parser


"""
Assumptions
	1) Grammar is augmented [ first production is S-> start_symbol ]
	2) All symbols are represented by single character only
	3) Non-terminals are represented by uppercase characters

"""

"""
Simple Expression Grammar

S -> E
E -> E+T | T
T -> T*F | F
F -> (E) | x

"""
grammar = [
    ["S", "E"],
    ["E", "E+T"],
    ["E", "T"],
    ["T", "T*F"],
    ["T", "F"],
    ["F", "(E)"],
    ["F", "x"]
]

"""
Any String ending with abb

S -> A
A -> aA | bA | aB 
B -> bC
C -> bD
D -> epsilon

"""
grammar2 = [
    ["S", "A"],
    ["A", "aA"],
    ["A", "bA"],
    ["A", "aB"],
    ["B", "bC"],
    ["C", "bD"],
    ["D", ""]
]

print("\nExample 1\n")
slr = get_lr_parser(grammar)
slr("x*x+x")

print("\nExample 2\n")
slr = get_lr_parser(grammar2)
slr("ababb")
