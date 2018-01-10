total = 1
r = ["00","01","11","10"]
bname = "punched_card_"
for i in r:
    for j in r:
        for k in r:
            for l in r:
                for m in r:
                    print(bname + str(total) + '.json: ' + i + ", " + j + ", " + k + ", " + l + "," + m)
                    with open(bname + str(total) + '.json', 'w') as f:
                        f.write('{"parent": "item/generated",')
                        f.write('\n"textures": {')
                        f.write('"layer0": "sburbmod:items/punched_card_color",\n        "layer1": "sburbmod:items/punched_card_whiteback",')
                        f.write('\n    "layer2": "sburbmod:items/holes/holes_1_' + i + '",')
                        f.write('\n    "layer3": "sburbmod:items/holes/holes_2_' + j + '",')
                        f.write('\n    "layer4": "sburbmod:items/holes/holes_3_' + k + '",')
                        f.write('\n    "layer5": "sburbmod:items/holes/holes_4_' + l + '",')
                        f.write('\n    "layer6": "sburbmod:items/holes/holes_5_' + m + '"')
                        f.write('\n}}')
                    total += 1