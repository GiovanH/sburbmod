total = 1
top = 2
mtop = 7
mbottom = 7
bottom = 2

for i in range(top):
    for j in range(mtop):
        for k in range(mbottom):
            for l in range(bottom):
                print('dowel_carved_' + str(total) + '.json: ' + str(i+1) + ", " + str(j+1) + ", " + str(k+1) + ", " + str(l+1))
                with open('dowel_carved_' + str(total) + '.json', 'w') as f:
                    f.write('{"parent": "item/generated",')
                    f.write('\n"textures": {')
                    f.write('\n    "layer0": "sburbmod:items/carvings/carvings_1_' + str(i+1) + '",')
                    f.write('\n    "layer1": "sburbmod:items/carvings/carvings_2_' + str(j+1) + '",')
                    f.write('\n    "layer2": "sburbmod:items/carvings/carvings_3_' + str(k+1) + '",')
                    f.write('\n    "layer3": "sburbmod:items/carvings/carvings_4_' + str(l+1) + '"')
                    f.write('\n}}')
                total += 1