# HBMZZ-MOD-addon

`hbmzz:addon` —— 从 [HBMZZ](https://github.com/ysb-discowave/HBMZZ-MOD) 拆出的控制台/附属内容。

这个 jar 是**根 mod 的附属包**，不能单独使用。它包含控制面板里那些来自 NTM Cursed Addon 的
节点与控件资源（addon 模型、贴图、控件面板的 solid/invisible GUI 贴图），这些内容的许可证
与根 mod 不同，因此单独放在一个仓库里声明、单独发一个 jar。

**装法：两个 jar 都要装。** 把 `hbmzz-<版本>.jar` 和 `hbmzz-addon-<版本>.jar` 一起放进 `mods/`
目录。只装 addon 不会有效果（它没有方块、没有物品）；只装根 mod 则 addon 那几个控件会缺模型。

## 依赖关系

- modid：`hbmzzaddon`
- 对根 mod 是**软依赖**：`after:hbmzz`（若根 mod 存在则在后加载），**不是** `required-after`。
  反过来根 mod 不依赖本包，可以单独安装。
- 需要 Minecraft 1.12.2 + Forge（`1.12.2-14.23.5.2847`）。
- 资源域沿用 `assets/hbmzz/`，**不是** `assets/hbmzzaddon/`：注册名与贴图路径必须和拆分前一致，
  否则旧存档里的方块与方块实体找不到模型。

## 许可

**PolyForm Shield License 1.0.0** —— 全文见 [`LICENSE-PolyForm.txt`](LICENSE-PolyForm.txt)，
来源与必须原样保留的 `Required Notice` 行见 [`NOTICE-hbmzz-addon.md`](NOTICE-hbmzz-addon.md)。

要点：可再分发、可修改；**不得 sublicense**（不能改成 GPL/MIT 等再发布）；含 noncompete 条款；
再分发须随附条款全文或 URL 及 `Required Notice` 原文。

本仓库**不是** GPL-3.0。根 mod `hbmzz` 是 GPL-3.0-only，两者分开声明、分开发，请勿混为一谈。

## 从源码构建

需要 JDK 8 与 Gradle 4.10.3（ForgeGradle 2.3 不支持更高版本的 Gradle）：

```sh
export JAVA_HOME="<jdk8>"
"$JAVA_HOME/bin/java" -Xmx3G \
  -classpath "<gradle-4.10.3>/lib/gradle-launcher-4.10.3.jar" \
  org.gradle.launcher.GradleMain build
```

产物：`build/libs/hbmzz-addon-<版本>.jar`（首次构建会下载并反编译 Minecraft，需要联网；
之后可用 `--offline`）。许可文件会被打进 jar 的 `META-INF/`。

## 目录

```
build.gradle                      ForgeGradle 2.3 / 1.12.2-14.23.5.2847
src/main/java/com/hbmzz/addon/    仅一个 @Mod 入口（本包目前只带资源）
src/main/resources/mcmod.info     modid hbmzzaddon，after:hbmzz
src/main/resources/assets/hbmzz/  models/control_panel/addon/**、
                                  textures/models/control_panel/addon/**、
                                  textures/gui/control_panel/{solid,solid_emissive,invisible}.png
LICENSE-PolyForm.txt              PolyForm Shield 1.0.0 全文（逐字节照抄上游）
NOTICE-hbmzz-addon.md             来源、commit、Required Notice、覆盖范围
```
