package pl.duelstats;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class DuelStatsScreen extends Screen {
    public DuelStatsScreen() {
        super(Text.literal("Duel Stats"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int top = this.height / 2 - 65;

        addDrawableChild(ButtonWidget.builder(Text.literal("START / RESET"), button -> {
            DuelTracker.start();
        }).dimensions(centerX - 100, top + 75, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("STOP"), button -> {
            DuelTracker.stop();
        }).dimensions(centerX - 100, top + 99, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Zamknij"), button -> {
            if (client != null) client.setScreen(null);
        }).dimensions(centerX - 100, top + 123, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        int centerX = width / 2;
        int y = height / 2 - 75;
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("DUEL STATS"), centerX, y, 0xFFFFFF);
        y += 18;
        String state = DuelTracker.isActive() ? "STATUS: AKTYWNY" :
                DuelTracker.isFinished() ? "STATUS: ZAKOŃCZONY" : "STATUS: GOTOWY";
        context.drawCenteredTextWithShadow(textRenderer, Text.literal(state), centerX, y, 0xAAAAAA);
        y += 18;
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Próby ataku: " + DuelTracker.attempts()), centerX, y, 0xFFFFFF);
        y += 14;
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Trafienia: " + DuelTracker.hits()), centerX, y, 0x55FF55);
        y += 14;
        context.drawCenteredTextWithShadow(textRenderer,
                Text.literal(String.format(java.util.Locale.ROOT, "Celność: %.1f%%", DuelTracker.accuracy())),
                centerX, y, 0xFFFF55);
        y += 14;
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Combo teraz: " + DuelTracker.currentCombo()), centerX, y, 0xFFFFFF);
        y += 14;
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Najdłuższe combo: " + DuelTracker.longestCombo()), centerX, y, 0xFFAA00);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
