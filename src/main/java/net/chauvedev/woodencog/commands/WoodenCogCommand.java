package net.chauvedev.woodencog.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.chauvedev.woodencog.WoodenCog;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

import java.awt.*;

public class WoodenCogCommand {

    public WoodenCogCommand(WoodenCog cog){


    }
    public void register(CommandDispatcher<CommandSourceStack> dispatcher){
        LiteralArgumentBuilder<CommandSourceStack> woodencogcommand
                = Commands.literal("woodencog")
                .requires((commandSource) -> commandSource.hasPermission(1))
                .then(Commands.literal("python")
                        .executes(commandContext -> sendMessage(commandContext, "this python"))
                )
                .executes(commandContext -> sendMessage(commandContext, "Nothing to say!"));  // blank: didn't match a literal or the custommessage argument
        dispatcher.register(woodencogcommand);
    }

    private int sendMessage(CommandContext<CommandSourceStack> commandContext, String s) throws CommandSyntaxException {
        commandContext.getSource().sendSuccess(new TextComponent(s),true);

        return 1;
    }


}
