/**
* Copyright (c) Lambda Innovation, 2013-2016
* This file is part of the AcademyCraft mod.
* https://github.com/LambdaInnovation/AcademyCraft
* Licensed under GPLv3, see project root for more information.
*/
package cn.academy.vanilla.meltdowner;

import cn.academy.ability.api.Category;
import cn.academy.ability.api.Skill;
import cn.academy.vanilla.ModuleVanilla;
import cn.academy.vanilla.meltdowner.skill.*;

/**
 * @author WeAthFolD
 */
public class CatMeltdowner extends Category {

    public static final Skill
        electronBomb = ElectronBomb.instance,
        radIntensify = RadiationIntensify.instance,
        rayBarrage = RayBarrage.instance,
        scatterBomb = ScatterBomb.instance,
        lightShield = LightShield.instance,
        meltdowner = Meltdowner.instance,
        jetEngine = JetEngine.instance,
        mineRayBasic = MineRayBasic.instance,
        mineRayExpert = MineRayExpert.instance,
        mineRayLuck = MineRayLuck.instance,
        electronMissile = ElectronMissile.instance;

    public CatMeltdowner() {
        super("meltdowner");
        this.colorStyle.setColor4i(126, 255, 132, 80);

        // Lv1
        this.addSkill(electronBomb);
        this.addSkill(radIntensify);

        // Lv2
        this.addSkill(scatterBomb);
        this.addSkill(lightShield);

        // Lv3
        this.addSkill(meltdowner);
        this.addSkill(mineRayBasic);

        // Lv4
        this.addSkill(rayBarrage);
        this.addSkill(jetEngine);
        this.addSkill(mineRayExpert);

        // Lv5
        this.addSkill(mineRayLuck);
        this.addSkill(electronMissile);

        ModuleVanilla.addGenericSkills(this);

        // Deps
        scatterBomb.setParent(electronBomb, 0.8f);
        radIntensify.setParent(electronBomb, 0.5f);
        lightShield.setParent(electronBomb, 1.0f);
        meltdowner.setParent(scatterBomb, 0.8f);
        meltdowner.addSkillDep(lightShield, 0.8f);
        mineRayBasic.setParent(meltdowner, 0.3f);
        rayBarrage.setParent(meltdowner, 0.5f);
        jetEngine.setParent(meltdowner, 1.0f);
        mineRayExpert.setParent(mineRayBasic, 0.8f);
        mineRayLuck.setParent(mineRayExpert, 1.0f);
        electronMissile.setParent(jetEngine, 0.3f);
    }

}
