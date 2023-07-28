package aeyama.world.blocks.storage;

import arc.*;
import arc.graphics.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;

import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class MultiCoreBlock extends CoreBlock {
    public Seq<UnitType> unitTypes = new Seq<>();

    public MultiCoreBlock(String name) {
        super(name);

        configurable = true;

        config(UnitType.class, MultiCoreBuild::setCurrentUnit);
    }

    @Override
    public void init() {
        super.init();

        unitType = unitTypes.first();
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.remove(Stat.unitType);
        stats.add(new Stat("unitTypes", StatCat.function), t -> {
            t.row();
            for(UnitType unit : unitTypes) {
                t.table(Styles.grayPanel, b -> {
                    b.image(unit.uiIcon).size(40f).pad(10f).left().scaling(Scaling.fit);
                    b.table(i -> {
                        i.add(unit.localizedName).left();
                        if (Core.settings.getBool("console")) {
                            i.row();
                            i.add(unit.name).left().color(Color.lightGray);
                        }
                    });
                    b.button("?", Styles.flatBordert, () -> ui.content.show(unit)).size(40f).pad(10).right().grow().visible(() -> unit.unlockedNow());
                }).growX().pad(5f).row();
            }
        });
    }

    public class MultiCoreBuild extends CoreBuild {
        
        public void setCurrentUnit(UnitType unit) {
            if (unitType != unit)
                unitType = unit;
            
            requestSpawn(player);
        }

        public void build(MultiCoreBlock b, MultiCoreBuild c, Table table) {
            for (UnitType unit : unitTypes) {
                int index = unitTypes.indexOf(unit);
                if (index != 0 && index % 2 == 0)
                    table.row();
                
                table.table(Styles.grayPanel, t -> {
                    ImageButton button = new ImageButton(unit.uiIcon, Styles.clearTogglei);
                    button.changed(() -> c.configure(unit));
                    button.update(() -> button.setChecked(b.unitType == unit));
                    t.add(button).size(40f).row();
                    t.add(unit.localizedName).center();
                }).grow().pad(2f).margin(5f);
            }

            table.left().setSize(128f);
        }

        @Override
        public void buildConfiguration(Table table) {
            build(MultiCoreBlock.this, this, table);
        }
    }
}
